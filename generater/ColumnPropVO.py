# -*- coding: utf-8 -*-
"""
@file    : ColumnPropVO.py
@date    : 2020-01-15
@author  : carey
"""


class ColumnPropVO(object):
    def __init__(self, name, col_type, pri, desc, pri_type):
        self.name = name
        self.col_type = col_type
        self.pri = pri
        self.desc = desc
        self.pri_type = pri_type


