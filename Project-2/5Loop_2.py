# 5 switches with big loop
# 1 --- 2 --- 4
# |          /
# 3        /
# |       /
# |      /
# |    /
# |  /
# | /
# |/
# 5

topo = { 1 : [2, 3], 
         2 : [1, 4],
         3 : [1, 5], 
         4 : [2, 5],
         5 : [3, 4] }
