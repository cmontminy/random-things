import numpy

num_generations = 5
num_parents_mating = 4

#calculating the fitness value of each sol in the current pop
def cal_pop_fitness(equation_inputs, pop):
    # fitness funtion calculates the sum of products between each input and its corresponding weight
    fitness = numpy.sum(pop * equation_inputs, axis = 1)
    return fitness

# selecting the best individuals in the current gen as parents
def select_mating_pool(pop, fitness, num_parents):
    # creates an empty array based on number of parents
    parents = numpy.empty((num_parents, pop.shape[1]))

    for parent_num in range(num_parents):
        # gets index of highest fitness
        max_fitness_idx = numpy.where(fitness == numpy.max(fitness))
        max_fitness_idx = max_fitness_idx[0][0]
        # retrieve sol from index
        parents[parent_num, :] = pop[max_fitness_idx, :]
        # set this index to be hecka small so it doesnt get picked again
        fitness[max_fitness_idx] = -99999999999
    return parents

def crossover(parents, offspring_size) :
    # create empty array based on offspring size
    offspring = numpy.empty(offspring_size)
    # point at which crossover takes place between two parents, typically at the center
    crossover_point = numpy.uint8(offspring_size[1]/2)

    for k in range(offspring_size[0]) :
        # index of the first parent to mate
        parent1_idx = k%parents.shape[0]
        # index of the second parent to mate
        parent2_idx = (k + 1)%parents.shape[0]
        # the new offspring will have its first half of genes from the first parent
        offspring[k, 0:crossover_point] = parents[parent1_idx, 0:crossover_point]
        # second half of genes from the second parent
        offspring[k, crossover_point:] = parents[parent2_idx, crossover_point:]
    return offspring

# mutation changes a single gene in each offspring randomly
def mutation(offspring_crossover) :
    for idx in range(offspring_crossover.shape[0]) :
        # random value between -1 and 1 to be added to the gene
        random_value = numpy.random.uniform(-1.0, 1.0, 1)
        offspring_crossover[idx, 4] = offspring_crossover[idx, 4] + random_value
    return offspring_crossover