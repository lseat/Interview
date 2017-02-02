# Hourglass Topology
# 1--2
#  \ |
#    3
#    | \
#    4--5

topo = { 1 : [2, 3],
         2 : [1, 3],
         3 : [1, 2, 4, 5],
         4 : [3, 5],
         5 : [3, 4] }
