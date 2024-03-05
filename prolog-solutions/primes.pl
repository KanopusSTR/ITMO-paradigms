fill(N, R) :-
	R > N, !.
	
fill(N, R) :-
	assert(prime(R)),
	NextR is R + 1,
	fill(N, NextR).

thin(N, R, S) :-
	R > N, !.

thin(N, R, S) :-
	\+ prime(R),
	NextR is R + S,
	thin(N, NextR, S).
	
thin(N, R, S) :-
	retract(prime(R)),
	NextR is R + S,
	thin(N, NextR, S).
	
init_1(N, C) :-
	C_sqr2 is C * C,
	C_sqr2 > N, !.
	
init_1(N, C) :-
	\+ prime(C),
	NextC is C + 1,
	init_1(N, NextC).
	
init_1(N, C) :-
	R is C * C,
	thin(N, R, C),
	NextC is C + 1,
	init_1(N, NextC).

init(N) :-
	fill(N, 2),
	init_1(N, 2).
	
composite(N) :- 
	N > 3, 
	\+ prime(N).

prime_divisors_it(1, _, []).

prime_divisors_it(Number, PP, [Pr | T]):-
	prime(Pr),
	Pr >= PP,
	0 is mod(Number, Pr),
	NextNumber is Number / Pr,
	prime_divisors_it(NextNumber, Pr, T).

prime_divisors(Number, Divisors) :-
	prime_divisors_it(Number, 1, Divisors).

unique_prime_divisors_it(1, _, []).

unique_prime_divisors_it_com(Number, PP, Pr):-
	prime(Pr),
	Pr >= PP,
	0 is mod(Number, Pr).

unique_prime_divisors_it(Number, PP, [Pr | T]):-
	unique_prime_divisors_it_com(Number, PP, Pr),
	NextNumber is Number / Pr,
	0 is mod(NextNumber, Pr),
	unique_prime_divisors_it(NextNumber, Pr, [P | T]).

unique_prime_divisors_it(Number, PP, [Pr | T]):-
	unique_prime_divisors_it_com(Number, PP, Pr),
	NextNumber is Number / Pr,
	\+ 0 is mod(NextNumber, Pr),
	unique_prime_divisors_it(NextNumber, Pr, T).

unique_prime_divisors(Number, Divisors) :-
	unique_prime_divisors_it(Number, 1, Divisors).
