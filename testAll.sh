#! /usr/bin/env sh
echo "##########################"
echo "### Running e2e tests! ###"
echo "##########################\n"
count=0 # number of test cases run so far

StringTests="helpStr noCom ListAllNoBooks"

for test in tests/*.in; do
    name=$(basename $test .in)
    echo Running test $name!
    expected_file=tests/$name.out
    diff $expected_file <(java Library <$test) || echo "Test $name: failed!\n"
    
    count=$((count+1))
done

echo "Finished running $count tests!"
