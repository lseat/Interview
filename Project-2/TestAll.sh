#!/usr/bin/env bash

# Execute run_spanning_tree.py for every *.txt file that also has a *.py file.
foundDiffs=0
for f in *.txt
do
    # if [ "$f" != "output.txt" ] && [ "$f" != "testing.txt" ]
    if [ -f "${f%.txt}.py" ]
    then
        echo "Checking ${f%.txt}"
        python run_spanning_tree.py "${f%.txt}" testing.txt > /dev/null
        diff testing.txt "$f" > /dev/null
        if [ $? -ne 0 ]
        then
            echo "*** Diffs found in $f!"
            cp testing.txt "${f%.txt}.Wrong.txt"
            foundDiffs=1
        elif [ -f "${f%.txt}.Wrong.txt" ]
        then
            rm "${f%.txt}.Wrong.txt"
        fi
    fi
done

echo
if [ "$foundDiffs" = "0" ]
then
    echo "*** No diffs found! :-)"
else
    echo "*** Diffs were found! :-("
fi