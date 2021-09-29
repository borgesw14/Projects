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
    newKeys = []
    newValues = []

    with open(working_dir + '/data/german/n-gram counts/' + inputfile, 'r') as f:
        data = f.readlines()
        data.reverse()
        keys = []
        values = []

        gerChar = ["Ä", "Ü", "Ö"]
        engChar = ["A", "U", "O"]

        for line in data:
            x = re.split("\s", line)

            keys.append(x[0])
            values.append(x[1])
        
        

        for j,c in enumerate(gerChar):
            for i, key in enumerate(keys):
                keys[i] = key.replace(key, re.sub(c, engChar[j], key))


        for i in range(len(keys)):
            
            for j in range(i+1, len(keys)):
                
                if(keys[i] == keys[j]):
                    sum = int(values[i]) + int(values[j])

                    values[i] = sum
                    values[j] = sum

        [newKeys.append(x) for x in keys if x not in newKeys]
        [newValues.append(int(x)) for x in values if x not in newValues]

        res = {newKeys[i]: newValues[i] for i in range(len(newKeys))}

    with open(working_dir + '/data/german/' + outputfile, 'w') as w:
        sort = sorted(res.items(), key=lambda x: x[1], reverse=False)

        for i in sort:
            w.write(i[0] + " " + str(i[1]) + "\n")


if __name__ == "__main__":
   main(sys.argv[1:])



