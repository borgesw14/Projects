import os

working_dir = os.getcwd()

with open(working_dir + '/data/german/n-gram counts/german_monograms.txt', 'r') as f:
    data = f.read()
    print('git isnt working')