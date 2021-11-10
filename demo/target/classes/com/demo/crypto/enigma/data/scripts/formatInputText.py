import os, re, sys, getopt

def main(argv):
    inputfile = ''
    outputfile = ''
    try:
        opts, args = getopt.getopt(argv,"hi:o:",["ifile=","ofile="])
    except getopt.GetoptError:
        print('test.py -i <inputfile> -o <outputfile>')
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print ('test.py -i <inputfile> -o <outputfile>')
            sys.exit()
        elif opt in ("-i", "--ifile"):
            inputfile = arg
        elif opt in ("-o", "--ofile"):
            outputfile = arg
   
    working_dir = os.getcwd()
    output = ""

    with open(working_dir + '/data/german/n-gram counts/' + inputfile, 'r') as f:
        data = f.readlines()

        orgChar = ["Ä", "Ü", "Ö", "ß"] # original char
        repChar = ["A", "U", "O", "S"] # replacement char

        # individual char replacement 
        for j,c in enumerate(orgChar):
            for i, key in enumerate(data):
                data[i] = key.replace(key, re.sub(c, repChar[j], key))
                output += data[i]

        # 'ch' -> 'c'
        for i, key in enumerate(data):
            data[i] = key.replace("ch|CH", "c")
        


    with open(working_dir + outputfile, 'w') as w:
        for i in output:
            w.write(i)



if __name__ == "__main__":
   main(sys.argv[1:])