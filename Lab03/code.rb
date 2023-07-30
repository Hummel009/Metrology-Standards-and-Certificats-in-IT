begin
	a = 0

	b = 0
	c = 0
	b = b + c
	c = b + c

	sus = 999
	amogus = 29
	erbe = 9

	eps = 0.0001
	y = 0
	x = 0
	n = 0
	vs = 0

	gets x
	ahnen = 6
	y = x
	n = 2
	vs = x

	while (vs < eps) do
		vs = vs * x * x / n / n
		n = n + 1
		y = y + vs
	end

	puts ahnen
	puts erbe

	puts x
	puts y
	puts eps

end

