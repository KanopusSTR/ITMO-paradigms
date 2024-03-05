:- load_library('alice.tuprolog.lib.DCGLibrary').

nvar(V, _) :- var(V).
nvar(V, T) :- nonvar(V), call(T).

lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

digits_p([]) --> [].
digits_p([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'])},
  [H],
  digits_p(T).

number_p(['-' | T]) -->
	['-'], digits_p(T).
number_p(T) -->
	digits_p(T).

var_p([]) --> [].
var_p([H | T]) -->
  { member(H, [x, y, z, 'X', 'Y', 'Z'])},
  [H],
  var_p(T).
	
op_p(op_add) --> ['+'].
op_p(op_subtract) --> ['-'].
op_p(op_multiply) --> ['*'].
op_p(op_divide) --> ['/'].
op_p(op_negate) --> ['n', 'e', 'g', 'a', 't', 'e'].

operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.
operation(op_negate, A, R) :- R is -A.

expr_p(variable(Name)) --> 
	{ nvar(Name, atom_chars(Name, Chars)) },
  var_p(Chars),
  { atom_chars(Name, Chars) }.

expr_p(const(Value)) -->
  { nvar(Value, number_chars(Value, Chars)) },
  number_p(Chars),
  { Chars \= ['-'], Chars = [_ | _], number_chars(Value, Chars) }.

expr_p(operation(Op, A, B)) --> 
	{ var(Op) -> Ws = []; Ws = [' '] },
	['('], expr_p(A), Ws, op_p(Op), Ws, expr_p(B), [')'].

expr_p(operation(Op, A)) --> 
	op_p(Op), ['('], expr_p(A), [')'].

skip_ws([], []).
skip_ws([' ' | T], RT) :- !, skip_ws(T, RT).
skip_ws([H | T], [H | RT]) :- skip_ws(T, RT).

infix_str(E, A) :- ground(E), phrase(expr_p(E), C), atom_chars(A, C).
infix_str(E, A) :- atom(A), atom_chars(A, C), skip_ws(C, NextC), phrase(expr_p(E), NextC).

evaluate(const(X), _, X).
evaluate(variable(X), Vars, R) :-
	atom_chars(X , [H | _]),
	lookup(H, Vars, R).
evaluate(operation(Op, A, B), Vars, R) :-
	evaluate(A, Vars, R1),
	evaluate(B, Vars, R2),
	operation(Op, R1, R2, R).

evaluate(operation(Op, A), Vars, R) :-
	evaluate(A, Vars, R1),
	operation(Op, R1, R).