# 4 Switches Simple Single Loop Topology:
# 1 --- 2
# |     |
# |     |
# 4 --- 3

topo = { 1 : [2, 4], 
         2 : [1, 3],
         3 : [2, 4], 
         4 : [1, 3] }
