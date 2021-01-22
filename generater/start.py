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
import os

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
vo_template = '../template/VO.java'
mapper_template = '../template/Mapper.java'
mapper_xml_template = '../template/mapper.xml'
service_template = '../template/Service.java'
service_impl_template = '../template/ServiceImpl.java'

# 生成文件的包地址
module_package = config.get('base', 'module_package')
do_package = module_package + 'entity'
vo_package = module_package + 'vo'
mapper_package = module_package + 'dao'
service_package = module_package + 'service'
service_impl_package = module_package + 'service.impl'
# 生成文件的目录
module_dir_root = config.get('base', 'module_dir_root')
do_dir_root = module_dir_root + 'entity/'
vo_dir_root = module_dir_root + 'vo/'
mapper_dir_root = module_dir_root + 'dao/'
service_dir_root = module_dir_root + 'service/'
service_impl_dir_root = module_dir_root + 'service/impl/'
xml_dir_root = config.get('base', 'xml_dir_root')
# 是否使用swagger
use_swagger = True if config.get('base', 'use_swagger') is 1 else False

common_attr = ['createTime', 'modifyTime']

# 生成文件
try:
    for table in table_list:
        print('########处理表' + table + '##########')
        entity_name = util.str2Hump(table)
        fields = []
        cursor.execute('show full columns from ' + table)
        columns = cursor.fetchall()
        # 表的所有字段及其属性
        for item in columns:
            column_prop = ColumnPropVO(util.col_trans_hump(item[0]), util.type_trans_java(item[1]),
                                       util.judge_pri(item[4]), item[8], item[6])
            fields.append(column_prop)

        # 写实体文件
        do_file_path = do_dir_root + entity_name + '.java'
        cursor.execute('select table_comment from information_schema.TABLES where table_schema=\''
                       + config.get('database', 'repository') + '\' and table_name=\'' + table + '\'')
        table_desc = cursor.fetchone()[0]
        # 先写一些公共的部分
        with open(do_template, 'r') as do:
            with open(do_file_path, 'w', encoding='utf8') as java:
                for line in do.readlines():
                    new_line = line.format(package=do_package, author=author, now=now, table_name=table,
                                           do_name=entity_name, table_desc=table_desc)
                    java.write(new_line)
                java.write(' {\n')
        # 写字段部分
        with open(do_file_path, 'a', encoding='utf8') as java:
            for field in fields:
                if field.desc is not None and field.desc != '':
                    java.writelines('   /** ' + field.desc + ' */\n')
                if field.pri:
                    if field.pri_type == 'auto_increment':
                        java.writelines('   @TableId(value = \"' + field.name + '\",type = IdType.AUTO)\n')
                    else:
                        java.writelines('   @TableId(\"' + field.name + '\")\n')
                else:
                    java.writelines('   @TableField(\"' + field.name + '\")\n')
                java.writelines('   private ' + field.col_type + ' ' + field.name + ';\n')
            java.write('}')

        # 写vo文件
        vo_file_path = vo_dir_root + entity_name + 'VO.java'
        vo_class = entity_name + 'VO';
        # 先写一些公共的部分
        with open(vo_template, 'r') as do:
            with open(vo_file_path, 'w', encoding='utf8') as java:
                for line in do.readlines():
                    new_line = line.format(package=vo_package, author=author, now=now, table_name=table,
                                           vo_class=vo_class, table_desc=table_desc)
                    java.write(new_line)
                java.write(' {\n')
        # 写字段部分
        with open(vo_file_path, 'a', encoding='utf8') as java:
            for field in fields:
                if field.name not in common_attr:
                    if use_swagger:
                        java.writelines('    @ApiModelProperty(value = \"{}\")'.format(field.desc))
                    java.writelines('   private ' + field.col_type + ' ' + field.name + ';\n')
            java.write('}')

        # 写mapper.java
        mapper_file_path = mapper_dir_root + entity_name + 'Mapper.java'
        with open(mapper_template, 'r') as do:
            with open(mapper_file_path, 'w', encoding='utf8') as java:
                for line in do.readlines():
                    new_line = line.format(package=mapper_package, author=author, now=now, table_name=table,
                                           do_name=entity_name, mapperName=entity_name + 'Mapper',
                                           do_position=do_package + '.' + entity_name)
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

        # 写service.java
        service_file_path = service_dir_root + entity_name + 'Service.java'
        vo_name = util.firstCharLower(vo_class)
        with open(service_template, 'r') as do:
            with open(service_file_path, 'w', encoding='utf8') as java:
                for line in do.readlines():
                    new_line = line.format(package=service_package, author=author, now=now,
                                           do_name=entity_name,
                                           vo_class=vo_class,
                                           vo_name=vo_name,
                                           service_name=entity_name + 'Service',
                                           do_position=do_package + '.' + entity_name,
                                           vo_position=vo_package + '.' + entity_name + 'VO')
                    if '>' in new_line:
                        new_line = new_line + '{'
                    java.write(new_line)
                java.write('}')

        # service_impl_file_path = service_impl_dir_root + entity_name + 'ServiceImpl.java'
        # with open(service_impl_template, 'r') as do:
        #     with open(service_impl_file_path, 'w', encoding='utf8') as java:
        #         for line in do.readlines():
        #             new_line = line.format(package=mapper_package, author=author, now=now,
        #                                    do_name=entity_name,
        #                                    vo_class=vo_class,
        #                                    vo_name=vo_name,
        #                                    service_name=entity_name + 'Service',
        #                                    do_position=do_package + entity_name,
        #                                    vo_position=vo_package + entity_name + 'VO')
        #             java.write(new_line)

        print("处理表{}结束".format(table))


finally:
    db.close()
