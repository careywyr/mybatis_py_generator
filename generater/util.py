# -*- coding: utf-8 -*-
"""
@file    : util.py
@date    : 2020-01-14
@author  : carey
"""

import re


def col_trans_hump(column):
    """
    字段名转驼峰
    :param column:
    :return:
    """
    underlines = []
    prop = list(column)
    for index, item in enumerate(column):
        if item == '_':
            underlines.append(index)

    for underline in underlines:
        prop[underline + 1] = str.upper(prop[underline + 1])
    return "".join(prop).replace('_', '')


def type_trans_java(col_type):
    """
    数据库属性转java属性
    :param col_type:
    :return:
    """
    if 'varchar' in col_type or 'char' in col_type:
        return 'String'
    if 'bigint' in col_type:
        return 'Long'
    if 'int' in col_type:
        return 'Integer'
    if 'decimal' in col_type:
        return 'BigDecimal'
    if 'datetime' in col_type or 'date' in col_type:
        return 'Date'


def judge_pri(pri):
    if pri == 'PRI':
        return True
    return False


def hump2underline(hunp_str):
    """
    驼峰形式字符串转成下划线形式
    :param hump_str: 驼峰形式字符串
    :return: 字母全小写的下划线形式字符串
    """
    # 匹配正则，匹配小写字母和大写字母的分界位置
    p = re.compile(r'([a-z]|\d)([A-Z])')
    # 这里第二个参数使用了正则分组的后向引用
    sub = re.sub(p, r'\1_\2', hunp_str).lower()
    return sub


def str2Hump(text):
    arr = filter(None, text.lower().split('_'))
    res = ''
    for i in arr:
        res = res + i[0].upper() + i[1:]
    return res


# 首字母小转换函数
def firstCharLower(s):
    return s[:1].lower() + s[1:]
