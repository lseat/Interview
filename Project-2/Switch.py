# Project 2 for OMS6250
#
# This defines a Switch that can can send and receive spanning tree 
# messages to converge on a final loop free forwarding topology.  This
# class is a child class (specialization) of the StpSwitch class.  To 
# remain within the spirit of the project, the only inherited members
# functions the student is permitted to use are:
#
# self.switchID                   (the ID number of this switch object)
# self.links                      (the list of swtich IDs connected to this switch object)
# self.send_message(Message msg)  (Sends a Message object to another switch)
#
# Student code should NOT access the following members, otherwise they may violate
# the spirit of the project:
#
# topolink (parameter passed to initialization function)
# self.topology (link to the greater topology structure used for message passing)
#
# Copyright 2016 Michael Brown
#           Based on prior work by Sean Donovan, 2015
      									    			
from Message import *
from StpSwitch import *

class Switch(StpSwitch):

    def __init__(self, idNum, topolink, neighbors):    
        # Invoke the super class constructor, which makes available to this object the following members:
        # -self.switchID                   (the ID number of this switch object)
        # -self.links                      (the list of swtich IDs connected to this switch object)
        super(Switch, self).__init__(idNum, topolink, neighbors)
        
        #TODO: Define a data structure to keep track of which links are part of / not part of the spanning tree.
        self.prev = None
        self.remaining_links = set()

    def send_initial_messages(self):
        #TODO: This function needs to create and send the initial messages from this switch.
        #      Messages are sent via the superclass method send_message(Message msg) - see Message.py.
        for neighbor in self.links:
            self.topology.send_message(Message(self.switchID, 1, self.switchID, neighbor, False))
        return
        
    def process_message(self, message):
        #TODO: This function needs to accept an incoming message and process it accordingly.
        #      This function is called every time the switch receives a new message.
        if self.prev and self.prev.switchID == message.origin:
            return
        root, distance = self.get_root_and_count()
        origin = self.topology.switches.get(message.origin)
        root2, distance2 = origin.get_root_and_count()
        distance2 += 1
        # print '{}:list {}:prev {} -> {}:list {}: prev {}'.format(
        #     origin.switchID, origin.remaining_links, origin.prev.switchID if origin.prev else 'none', 
        #     self.switchID, self.remaining_links, self.prev.switchID if self.prev else 'none')
        if root2.switchID > root.switchID:
            self.topology.send_message(Message(root.switchID, distance + 1, self.switchID, message.origin, False))
            if origin.switchID in self.remaining_links:
                self.remaining_links.remove(origin.switchID)
                origin.remaining_links.remove(self.switchID)
        elif root2.switchID < root.switchID:
            if self.prev:
                self.remaining_links.remove(self.prev.switchID)
                self.prev.remaining_links.remove(self.switchID)
            self.prev = origin
            self.remaining_links.add(origin.switchID)
            origin.remaining_links.add(self.switchID)
            for neighbor in self.links:
                if neighbor !=  origin.switchID:
                    self.topology.send_message(Message(root2.switchID, distance2 + 1, self.switchID, neighbor, False))
        else:
            if distance2 < distance or (distance2 == distance and self.prev and self.prev.switchID > origin.switchID):
                if self.prev:
                    self.remaining_links.remove(self.prev.switchID)
                    self.prev.remaining_links.remove(self.switchID)
                self.prev = origin
                self.remaining_links.add(origin.switchID)
                origin.remaining_links.add(self.switchID)
                for neighbor in self.links:
                    if neighbor !=  origin.switchID:
                        self.topology.send_message(Message(root2.switchID, distance2 + 1, self.switchID, neighbor, False))
            elif distance2 > distance + 1:
                self.topology.send_message(Message(root.switchID, distance + 1, self.switchID, message.origin, False))
        return

    def get_root_and_count(self):
        count = 0
        temp = self.prev
        root = self
        while temp:
            root = temp
            count += 1
            temp = temp.prev
        return root, count
        
    def generate_logstring(self):
        #TODO: This function needs to return a logstring for this particular switch.  The
        #      string represents the active forwarding links for this switch and is invoked 
        #      only after the simulaton is complete.  Output the links included in the 
        #      spanning tree by destination switch ID in increasing order on a single line. 
        #      Print links as '(source switch id) - (destination switch id)', separating links 
        #      with a comma - ','.  
        #
        #      For example, given a spanning tree (1 ----- 2 ----- 3), a correct output string 
        #      for switch 2 would have the following text:
        #      2 - 1, 2 - 3
        #      A full example of a valid output file is included (output.txt) with the project skeleton.
        result = list()
        for link in sorted(list(self.remaining_links)):
            result.append('{0} - {1}'.format(self.switchID, link))
        return ', '.join(result)
