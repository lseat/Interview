# Simple 4 Switch With No Loop Topology:
# 1 --- 2 --- 3 --- 4

topo = { 1 : [2], 
         2 : [1, 3],
         3 : [2, 4],
         4 : [3] }
