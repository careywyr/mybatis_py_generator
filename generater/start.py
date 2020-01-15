# -*- coding: utf-8 -*-
"""
@file    : start.py
@date    : 2020-01-14
@author  : carey
"""

import configparser
import MySQLdb
import generater.util as util
from generater.ColumnPropVO import ColumnPropVO
import time

config = configparser.ConfigParser()
config.read('../template/config.conf', encoding='UTF-8')
# 读取一些基础配置
db = MySQLdb.connect(config.get('database', 'host'), config.get('database', 'username'),
                     config.get('database', 'password'), config.get('database', 'repository'), charset='utf8')
# 要生成的表
table_list = config.get('base', 'table').split(',')
cursor = db.cursor()
# java文件头注释的基础设置，作者和当前时间
author = config.get('base', 'author')
now = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())

# 三个文件的模版
do_template = '../template/DO.java'
mapper_template = '../template/Mapper.java'
mapper_xml_template = '../template/mapper.xml'

# 生成文件的目录
do_dir_root = config.get('base', 'do_dir_root')
mapper_dir_root = config.get('base', 'mapper_dir_root')
xml_dir_root = config.get('base', 'xml_dir_root')

# 生成文件
try:
    for table in table_list:
        print('########处理表' + table + '##########')
        entity_name = str.upper(table[6:7]) + table[7:]
        fields = []
        cursor.execute('show full columns from ' + table)
        columns = cursor.fetchall()
        # 表的所有字段及其属性
        for item in columns:
            column_prop = ColumnPropVO(util.col_trans_hump(item[0]), util.type_trans_java(item[1]),
                                       util.judge_pri(item[4]), item[8])
            fields.append(column_prop)

        # 写实体文件
        do_package = config.get('base', 'do_package')
        do_file_path = do_dir_root + entity_name + 'DO.java'
        cursor.execute('select table_comment from information_schema.TABLES where table_schema=\''
                       + config.get('database', 'repository') + '\' and table_name=\'' + table + '\'' )
        table_desc = cursor.fetchone()[0]
        # 先写一些公共的部分
        with open(do_template, 'r') as do:
            with open(do_file_path, 'w', encoding='utf8') as java:
                for line in do.readlines():
                    new_line = line.format(package=do_package, author=author, now=now, table_name=table,
                                           do_name=entity_name + 'DO', table_desc=table_desc)
                    java.write(new_line)
                java.write(' {\n')
        # 写字段部分
        with open(do_file_path, 'a', encoding='utf8') as java:
            for field in fields:
                if field.pri:
                    java.writelines('   @Id\n')
                    java.writelines('   @GeneratedValue(strategy = GenerationType.IDENTITY)\n')
                if field.desc is not None and field.desc != '':
                    java.writelines('   /** ' + field.desc + ' */\n')
                java.writelines('   private ' + field.col_type + ' ' + field.name + ';\n')
            java.write('}')

        # 写mapper.java
        mapper_package = config.get('base', 'mapper_package')
        mapper_file_path = mapper_dir_root + entity_name + 'Mapper.java'
        with open(mapper_template, 'r') as do:
            with open(mapper_file_path, 'w', encoding='utf8') as java:
                for line in do.readlines():
                    new_line = line.format(package=mapper_package, author=author, now=now, table_name=table,
                                           do_name=entity_name + 'DO', mapperName=entity_name + 'Mapper',
                                           do_position=do_package + entity_name + 'DO;')
                    java.write(new_line)
                java.write(' {\n\n}')

        # 写mapper.xml
        namespace = mapper_package + '.' + entity_name + 'Mapper'
        mapper_xml_file_path = xml_dir_root + entity_name + 'Mapper.xml'
        with open(mapper_xml_template, 'r') as do:
            with open(mapper_xml_file_path, 'w', encoding='utf8') as java:
                for line in do.readlines():
                    new_line = line.format(namespace=namespace)
                    java.write(new_line)
finally:
    db.close()
