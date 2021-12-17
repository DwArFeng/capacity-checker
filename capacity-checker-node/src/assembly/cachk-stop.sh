#!/bin/sh
# 程序的根目录
basedir=/usr/local/cachk

PID=$(cat $basedir/cachk.pid)
kill "$PID"
