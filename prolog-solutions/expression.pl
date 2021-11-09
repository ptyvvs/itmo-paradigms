:- load_library('alice.tuprolog.lib.DCGLibrary').

un(op_negate, A, R) :- R is -A.
bin(op_add, A, B, R) :- R is A + B.
bin(op_subtract, A, B, R) :- R is A - B.
bin(op_multiply, A, B, R) :- R is A * B.
bin(op_divide, A, B, R) :- R is A / B.

lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

evaluate(const(V), _, V).
evaluate(variable(Name), Vars, R) :- lookup(Name, Vars, R).
evaluate(operation(Op, A), Vars, R) :- 
    evaluate(A, Vars, AV),
    un(Op, AV, R).
evaluate(operation(Op, A, B), Vars, R) :-
    evaluate(A, Vars, AV),
    evaluate(B, Vars, BV),
    bin(Op, AV, BV, R).

nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

removeRepeatingWS([], []).
removeRepeatingWS([H, H | T], R) :- H = ' ', !, removeRepeatingWS([H | T], R).
removeRepeatingWS([H | T], [H | R]) :- removeRepeatingWS(T, R).

suf_ws --> [].
suf_ws --> [' '].

un_op_p(op_negate) --> ['n','e','g','a','t','e'].
bin_op_p(op_add) --> ['+'].
bin_op_p(op_subtract) --> ['-'].
bin_op_p(op_multiply) --> ['*'].
bin_op_p(op_divide) --> ['/'].

expr_p(variable(Name)) --> [Name], { member(Name, [x, y, z]) }.
suf_p(variable(Name)) --> suf_ws, expr_p(variable(Name)), suf_ws.

digits_p([]) --> [].
digits_p([H | T]) --> 
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'])},
  [H], 
  digits_p(T).
uni_digits_p([H, H1 | T]) --> 
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'])},
  [H], 
  digits_p([H1 | T]).

expr_p(const(Value)) --> 
    { nonvar(Value, number_chars(Value, Chars)) },
    uni_digits_p(Chars),
    { Chars = [_ | _], number_chars(Value, Chars) }.
suf_p(const(Value)) --> suf_ws, expr_p(const(Value)), suf_ws.

expr_p(operation(Op, A)) --> ['('], expr_p(A), [' '], un_op_p(Op), [')'].
expr_p(operation(Op, A, B)) --> ['('], expr_p(A), [' '], expr_p(B), [' '], bin_op_p(Op), [')'].
suf_p(operation(Op, A)) --> suf_ws ,['('], suf_p(A), [' '], un_op_p(Op), suf_ws, [')'], suf_ws.
suf_p(operation(Op, A, B)) --> suf_ws, ['('], suf_p(A), [' '], suf_p(B), [' '], bin_op_p(Op), suf_ws, [')'], suf_ws.


suffix_str(E, A) :- ground(E), phrase(expr_p(E), C), atom_chars(A, C).
suffix_str(E, A) :- atom(A), atom_chars(A, C), removeRepeatingWS(C, NewC), phrase(suf_p(E), NewC).