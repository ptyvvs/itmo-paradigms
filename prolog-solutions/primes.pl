division(X,Y) :- 0 is X mod Y, !.
division(X,Y) :- X > Y + 1, Y1 is Y + 1, division(X,Y1).

prime_table(2).
prime(N) :- prime_table(N), !.
prime(N) :- N > 1, not division(N, 2), assert(prime_table).

composite(N) :- composite_table(N), !.
composite(N) :- N > 1, not prime(N).

is_sorted([]) :- !.
is_sorted([N]) :- !.
is_sorted([H1, H2 | T]) :- H1 < H2 + 1, is_sorted([H2|T]).


min_prime_divisor(N, N) :- prime(N), !.
min_prime_divisor(N, R) :- min_prime_divisor(N, 2, R).
min_prime_divisor(N, N, N) :- prime(N).
min_prime_divisor(N, R, R) :- prime(R), R < N + 1, 0 is N mod R, !. 
min_prime_divisor(N, R, Res) :- R1 is R + 1, min_prime_divisor(N, R1, Res).

prime_divisors(N, []) :- 1 is N, !.
prime_divisors(N, [N]) :- prime(N), !.
prime_divisors(N, [H|T]) :- N > 0, min_prime_divisor(N, H), N1 is div(N,H), prime_divisors(N1,T), is_sorted([H|T]). 

concat(A, [], A) :- !.
concat([], B, B) :- !.
concat([H1|T1], [H2|T2], [H1|TR]):- H1 < H2, !, concat(T1, [H2|T2], TR).
concat(L1, [H2|T2], [H2|TR]):- concat(L1, T2, TR).  

square_divisors(N, Res) :- prime_divisors(N, R), concat(R, R, Res).