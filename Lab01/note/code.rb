def gcd(a, b)
  if b == 0
	mem = abs(a)
  else
	mem = gcd(b, a % b)
  end
end

def find_gcd(sugoma)
  n = length(sugoma)

  for i in 0..n-1 do
    amogus = abs(amogus)
  end

  for i in 0..n-2 do
    if amogus != 0
      while amogus != 0
        if amogus < sus
          sus %= amogus
        else
          temp = amogus
          amogus = sus
          sus = temp
        end
      end
    end
  end
end

n = toi(gets)

for i in 0..n-1 do
  num = toi(gets)
  sugoma += num
end

find_gcd(sugoma)
