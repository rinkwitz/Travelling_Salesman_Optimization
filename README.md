# Travelling Salesman Optimization

The aim of this project is to solve the well-known problem of the traveling salesman with the help of 
various optimization techniques. The results of the different techniques are visualized so that they 
can be directly compared.

## Problem Statement 

The problem of the salesman consists in the fact that the salesman tries to visit 
<img src="https://latex.codecogs.com/gif.latex?n" title="n" />
 different cities consecutively and returns at the end again to the starting city. 
 In the process the salesman tries to 
find a route that is as short as possible. The problem can be described as a complete, 
weighted graph 
<img src="https://latex.codecogs.com/gif.latex?G&space;=&space;(V,&space;E)" title="G = (V, E)" />. 
The cities can be described as nodes <img src="https://latex.codecogs.com/gif.latex?x_i\in&space;V" title="x_i\in V" /> and the distance between two cities 
<img src="https://latex.codecogs.com/gif.latex?x_i" title="x_i" /> and <img src="https://latex.codecogs.com/gif.latex?x_j" title="x_j" /> with <img src="https://latex.codecogs.com/gif.latex?i,&space;j\in\{1,&space;...,&space;n\}" title="i, j\in\{1, ..., n\}" /> and <img src="https://latex.codecogs.com/gif.latex?i\neq&space;j" title="i\neq j" /> as a weighted edge <img src="https://latex.codecogs.com/gif.latex?c_{ij}\in&space;E" title="c_{ij}\in E" />. For more information, 
see [Wikipedia](https://en.wikipedia.org/wiki/Travelling_salesman_problem).

## Path Representation

To represent the way of the traveling salesman in this project we use the direct enumeration of the 
successively visited cities. For this purpose the cities are numbered from <img src="https://latex.codecogs.com/gif.latex?1" title="1" />
 to <img src="https://latex.codecogs.com/gif.latex?n" title="n" />. A path <img src="https://latex.codecogs.com/gif.latex?P" title="P" />
  can then be described as a tuple
   <img src="https://latex.codecogs.com/gif.latex?P=(p_1,&space;...,&space;p_{n&plus;1})" title="P=(p_1, ..., p_{n+1})" />. In other words, the traveler travels from city <img src="https://latex.codecogs.com/gif.latex?p_i" title="p_i" />
    directly to city <img src="https://latex.codecogs.com/gif.latex?p_{i&plus;1}" title="p_{i+1}" /> for <img src="https://latex.codecogs.com/gif.latex?i=1,&space;...,&space;n" title="i=1, ..., n" />
    . If city <img src="https://latex.codecogs.com/gif.latex?s_0" title="s_0" /> is the starting point, we require that <img src="https://latex.codecogs.com/gif.latex?p_1=p_{n&plus;1}=s_0" title="p_1=p_{n+1}=s_0" />.

## Optimization

To solve the problem, compile and execute the main code in ```TravellingSalesman.java```. In this class you can specify all optimizers of interest and their parameters.

### Simulated Annealing
### Ant Colony Optimization

## Authors

* [Philip Rinkwitz](https://github.com/rinkwitz)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgements:

The formulas of this README were create using:
* [Codecogs online Latex editor](https://www.codecogs.com/latex/eqneditor.php)


--------------------------------------

```
PREFIX dbo: <http://dbpedia.org/ontology/> 
PREFIX dc: <http://purl.org/dc/elements/1.1/> 
```

<img src="https://latex.codecogs.com/gif.latex?n_D(C)" title="n_D(C)" />

<p align="center">
<img src="/img/Figure_1.png" alt="correlation figure 1" width="850">
<img src="/img/Figure_2.png" alt="correlation figure 2" width="850">
</p>

* Wikidata: ```https://query.wikidata.org/sparql```
* Factforge: ```http://factforge.net/repositories/ff-news```
* GraphDB: ```http://0.0.0.0:7200/repositories/projectworldbank```

* [RMLmapper](https://github.com/RMLio/rmlmapper-java/)
* [LIMES](https://github.com/dice-group/LIMES)
* [GraphDB](http://graphdb.ontotext.com/)