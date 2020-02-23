import numpy
import ga

# inputs of the equations
equation_inputs = []
# number of the weights we are looking to optimize
num_weights = len(equation_inputs)
# solutions per population
sol_per_pop = 8
num_parents_mating = 4

# define population size
pop_size = (sol_per_pop, num_weights) # The population will have sol_per_pop chromosome where each chromosome has num_weights genes
# creating the initial population
new_population = numpy.random.uniform(low = -4.0, high = 4.0, size = pop_size) # random for change
print(new_population)

best_outputs = []
num_generations = 1000
for generation in range(num_generations):
    print("Generation : ", generation)

    # measuring the fitness of each chromosome in the pop
    fitness = ga.cal_pop_fitness(equation_inputs, new_population)
    print("Fitness")
    print(fitness)

    # find and print best result in the current iteration
    best_outputs.append(numpy.max(numpy.sum(new_population * equation_inputs, axis = 1)))
    print("Best result : ", numpy.max(numpy.sum(new_population * equation_inputs, axis = 1)))

    # selecting the best parents in the pop for mating
    parents = ga.select_mating_pool(new_population, fitness, num_parents_mating)
    print("Parents")
    print(parents)

    # generating next generation using crossover
    offspring_crossover = ga.crossover(parents, offspring_size = (pop_size[0] - parents.shape[0], num_weights))
    print("Crossover")
    print(offspring_crossover)

    # adding some variations to the offspring using mutation
    offspring_mutation = ga.mutation(offspring_crossover)
    print("Mutation")
    print(offspring_mutation)

    # creating the new population based on the parents and offspring
    # keep parents in case the new offspring are dumb
    new_population[0:parents.shape[0], :] = parents
    new_population[parents.shape[0]:, :] = offspring_mutation

# Getting the best solution after iterating finishing all generations.
#At first, the fitness is calculated for each solution in the final generation.
fitness = ga.cal_pop_fitness(equation_inputs, new_population)
# Then return the index of that solution corresponding to the best fitness.
best_match_idx = numpy.where(fitness == numpy.max(fitness))

print("Best solution : ", new_population[best_match_idx, :])
print("Best solution fitness : ", fitness[best_match_idx])


import matplotlib.pyplot
matplotlib.pyplot.plot(best_outputs)
matplotlib.pyplot.xlabel("Iteration")
matplotlib.pyplot.ylabel("Fitness")
matplotlib.pyplot.show()