# School

A basic file management system for schools.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Setup](#setup)
- [Results](#results)
- [Contributing](#contributing)

## Overview

School is a basic file management system for schools. This helps teachers to manage the courses and study materials for their students.

## Features

- School has a list of teachers and students.
- Teachers can create subjects. Each subject can have multiple lessons. Each lesson may have multiple workbooks.
- A list of actions is attributed to each teacher.
- Action types are: ADD, DELETE, REORDER, MOVE
- Teachers can add, delete, reorder and move lessons and workbooks.
- Teachers can perform undo operations.
- A simple hashing technique is used to store the exact addresses of the nodes in the tree.
- We can use this hashed address to retrieve the exact node from the tree.
- The exact node obtained from the hashed address can be used to find the path from the root node to the exact node.
- The number of children for each node can be found.

## Technologies

- Java
- Object Oriented Programming(OOP)
- Data Structures(Tree, Queue, Stack, HashMap, ArrayList)
- Algorithms( Hashing, Tree Traversal, etc.)

## Setup

To run this project, you need to have Java installed on your system. Open the project in your favorite IDE and run the `Test.java` file to see the results. The main logic is in the `DigitalSchool.java` file. The `Test.java` file is used to test the main logic. The results are printed to `results.txt` file.

## Results

The results are located [here](./results.txt)

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

---

### Link to the project: [Github Repository](https://github.com/udattam/School)