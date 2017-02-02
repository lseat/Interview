# Simple 2 Switch With Single Loop Topology:
# 1 --- 2
# |    /
# |   /
# | /
# |/
# 3

topo = { 1 : [2, 3], 
         2 : [1, 3],
         3 : [1, 2] }
