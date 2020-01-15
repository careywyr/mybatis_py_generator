# -*- coding: utf-8 -*-
"""
@file    : util.py
@date    : 2020-01-14
@author  : carey
"""


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
    if 'varchar' in col_type:
        return 'String'
    if 'bigint' in col_type:
        return 'Long'
    if 'int' in col_type:
        return 'Integer'


def judge_pri(pri):
    if pri == 'PRI':
        return True
    return False
