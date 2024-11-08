# CSE-464-2024-dbandola
Course Project 1 for CSE 464 by Daniel Bandola

**PART ONE**

Feature 1: First Commit

Part of Feature 2 and 3 (forgot to add list of nodes): https://github.com/dbandolaASU/CSE-464-2024-dbandola/pull/1

Feature 2 List of Nodes: https://github.com/dbandolaASU/CSE-464-2024-dbandola/pull/2

Feature 4: https://github.com/dbandolaASU/CSE-464-2024-dbandola/pull/3
Make sure that the output image on the JUnit test looks like the image below
![{F1054053-637E-44B5-85C9-69B5EEB1132C}](https://github.com/user-attachments/assets/aefdd0d6-6eeb-4d3c-97fd-a749a740ade9)

FINAL CLEANUP COMMIT: https://github.com/dbandolaASU/CSE-464-2024-dbandola/pull/4

To compile: mvn package

To test: mvn test

Feature 1 Output:

Parsed graph from localTest.dot
Number of nodes: 3
Node Labels: [a, b, c]
Number of Edges: 2
Edge Labels: a -> b, b -> c
Graph output to file testOutput.dot!


Feature 2 Output:

Process finished with exit code 0
Parsed graph from localTest.dot
Duplicate Node: a
Node z added!
Node a already exists!
Node r added!

Process finished with exit code 0


Feature 3 Output: 

Parsed graph from localTest.dot
Duplicate Vertex: a -> b
Edge c to a added!

Process finished with exit code 0


Feature 4 Output: 

![{F1054053-637E-44B5-85C9-69B5EEB1132C}](https://github.com/user-attachments/assets/aefdd0d6-6eeb-4d3c-97fd-a749a740ade9)

**PART TWO**

#1 https://github.com/dbandolaASU/CSE-464-2024-dbandola/commit/d62cbcd7bcfcac36ad9fac5b72892fc28da23285
Feature 1: removeNode
Expected output: 
Parsed graph from localTest.dot
Node a removed!
Number of nodes: 2
Node Labels: [b, c]
Number of Edges: 1
Edge Labels: b -> c

Feature2: removeNodes
Expected output:
Parsed graph from localTest.dot
Node a removed!
Node b removed!
Number of nodes: 1
Node Labels: [c]
Number of Edges: 0
Edge Labels

Feature3: removeEdge
expected output:
Parsed graph from localTest.dot
Edge b to c was removed!
Number of nodes: 3
Node Labels: [a, b, c]
Number of Edges: 1
Edge Labels: a -> b
