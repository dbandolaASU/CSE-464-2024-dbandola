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

Template Design commit link: https://github.com/dbandolaASU/CSE-464-2024-dbandola/commit/0d315f8547cc53a39d1531a71f92bb88af678c9d

I used the template pattern to extract the common steps of BFS and DFS. These were processing neighbors, building the path, checking if the nodes were valid, and the graph traversal. Since BFS used a queue and DFS uses a stack, any code with functions specific to queues and stacks were overridden in the bfs and dfs methods respectively.


#3

Strategy Design Pattern commit link: https://github.com/dbandolaASU/CSE-464-2024-dbandola/commit/c714e104787a6e297561a34b9105145d71b2b7f0

I created and interface "SearchStrategy" which declared the method search. I then changed my BFS and DFS methods to implement SearchStrategy. Then in GraphSearch, I made it so that a SearchStrategy is declared and set to either BFS or DFS.

#4

Random Walk commit link: https://github.com/dbandolaASU/CSE-464-2024-dbandola/commit/c71b181c1d73f7b57e4370f2a7338a1124ea95e9

chooses a random node to go to from the src. it can not visit the same not again since it was leading to memory errors. Below is the traversal from a to c.

![{721D45A7-6BF8-419A-AE8D-2EE82E58E3A2}](https://github.com/user-attachments/assets/65bd476f-3149-4408-8f70-49614f23e39d)
