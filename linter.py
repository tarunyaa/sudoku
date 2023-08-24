import sys
import os.path
import re


def write_indented_file(lines_with_indent, file):
    indent_size = 4
    to_write = []
    for (line, depth) in lines_with_indent:
        curr_line = [' ' for x in range(indent_size * depth)]
        curr_line.append(line)
        curr_line.append('\n')
        to_write.append(''.join(curr_line))
    file_obj = open(file, mode='w')
    file_obj.writelines(to_write)
    file_obj.close()


def remove_excess_spacing(lines):
    '''
    Rule 1: if we see more than 2 consecutive empty lines, trim to just 1
    Rule 2: if this is the first line in a block of code, do not allow for an empty line, 
            unless it is the first line after the class declaration
    '''

    # first pass through applies rule 1
    cleaned = []
    for i in range(len(lines)):
        if i == 0 or i == len(lines) - 1:
            cleaned.append(lines[i])
        else:
            consec_empty_lines = lines[i] == '' and lines[i + 1] == ''
            if not consec_empty_lines:
                cleaned.append(lines[i])
        
    
    # second pass through applies rule 2
    refined = []
    for i in range(len(cleaned)):
        if i == 0:
            refined.append(cleaned[i])
        else:
            beginning_of_block = len(cleaned[i-1]) > 0 and cleaned[i - 1][-1] == '{'
            previous_is_class_header = 'class' in cleaned[i-1]
            empty_line = cleaned[i] == ''

            if not (beginning_of_block and empty_line and not previous_is_class_header):
                refined.append(cleaned[i])

    return refined

def is_comment(line):
    return line[0:2] == '//' or line[0:2] == '/*' or line[0] == '*'


def parse_lines(lines):
    indent_level, line_number, lines_with_indent = 0, 0, []
    for line in lines:
        # if empty, just add it
        if len(line) == 0:
            lines_with_indent.append((line, indent_level))
        
        # don't let comments mess with the indentation
        elif is_comment(line): lines_with_indent.append((line, indent_level))
        
        # move back on }, add line, move forward on {
        else:
            # fix - last char may not be brace if inline comment exists. instead search for existence of braces.
            contains_end_brace = '}' in set(line)
            contains_start_brace = '{' in set(line)
            
            if contains_end_brace and not contains_start_brace:
                indent_level -= 1
            
            lines_with_indent.append((line, indent_level))

            if contains_start_brace and not contains_end_brace:
                indent_level += 1

            line_number += 1
    return lines_with_indent

def headerify(lines):
    # if first line is a multiline comment (either /* or /**), do nothing, else add a header
    with_headers = []

    first_line = lines[0]
    header = []
    if first_line[0:2] != '/*':
        header.append('/**')
        header.append(' * Name: TODO')
        header.append(' * Pennkey: TODO')
        header.append(' * Execution: TODO')
        header.append(' *')
        header.append(' * Description: TODO')
        header.append('**/')
        with_headers += header
    with_headers.append(first_line)

    # look for method definitions, and if it has no multi-line comment above it, add one
    method_header_regex = '(@Override\s)*(@Test\s)*(public|protected|private|static|\s) +[\w\<\>\[\]]+\s+(\w+) *\([^\)]*\) *(\{?|[^;])'
    method_header = []
    method_header.append('/**')
    method_header.append(' * Inputs: TODO')
    method_header.append(' * Outputs: TODO')
    method_header.append(' * Description: TODO')
    method_header.append('*/')

    for line in lines[1::]:
        comment = len(line) > 0 and is_comment(line)
        if not comment:
            match_obj = re.search(method_header_regex, line)
            if match_obj is not None:
                if 'main' not in line:
                    # no header for tests
                    if '@Test' in with_headers[-1]:
                        pass
                    # override should have method header
                    elif '@Override' in with_headers[-1]:
                        if with_headers[-2] != '*/' and with_headers[-2] != '**/':
                            last = with_headers.pop()
                            with_headers += method_header
                            with_headers.append(last)
                    else:
                        if with_headers[-1] != '*/' and with_headers[-1] != '**/':
                            with_headers += method_header
        with_headers.append(line)

    return with_headers

def lint(file):
    # read in all lines, strip lines to remove whitespace in individual lines
    lines = [line.strip() for line in open(file).readlines()]

    # clean up spacing
    lines = remove_excess_spacing(lines)

    # add header, if missing
    lines = headerify(lines)

    # build indentation structure - list with items as tuple (line, depth)
    lines_with_indent = parse_lines(lines)

    # write output to same file that we read from
    write_indented_file(lines_with_indent, file)


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print('Please input a java file or *.java for all java files in this directory.')
    else:
        files = [filename for filename in sys.argv[1::] if len(filename.split('.')) > 1 and filename.split('.')[-1] == 'java']
        if len(files) == 0:
            print('Please input only .java files.')
        else:
            for filename in files:
                if filename != 'PennDraw.java':
                    lint(filename)
        