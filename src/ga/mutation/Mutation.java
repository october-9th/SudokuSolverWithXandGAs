package ga.mutation;

import ga.chromosome.Chromosome;

public interface Mutation {
	Chromosome mutate(Chromosome chromosome);
}
