# Power up: Typeclasses

Typeclasses solve the supply/demand problem of functions.


Functions can depend on other functions:

    def functionA() = {
      functionB() // functionA calls functionB. So it *depends* on functionB() to work
    }

Typeclasses help you know which function to depend on

Example without typeclasses

Example with typeclasses
