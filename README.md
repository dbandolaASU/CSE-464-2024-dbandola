# CSE-464-2024-dbandola
Course Project Part 3 for CSE 464 by Daniel Bandola 

**PART THREE**

#1

Refactor commit link: https://github.com/dbandolaASU/CSE-464-2024-dbandola/commit/620a403e9e5c996c497922e2129690f16b7dab94 

Refactor 1 Method Extraction: extracted the method of checking if a node exists to simplify the code
Refactor 2 Method Extraction: extracted the method of checking if an edge exists to simplify the code
Refactor 3 Combine Methods: since adding one or multiple nodes was similar in function, I combined them into a single method for simplicity
Refactor 4 Combine Methods: since remvoing one or multiple nodes was similar in function, I combined them into a single method for simplicity
Refactor 5 Code Simplification: Simplified the code for toString, it takes up less lines and looks less messy


#2

Template Design commit link: https://github.com/dbandolaASU/CSE-464-2024-dbandola/commit/6288fe304ce5c64b2f7387c19ea23fbba8e1dc55

I used the template pattern to extract the common steps of BFS and DFS. These were processing neighbors, building the path, checking if the nodes were valid, and the graph traversal. Since BFS used a queue and DFS uses a stack, any code with functions specific to queues and stacks were overridden in the bfs and dfs methods respectively.
