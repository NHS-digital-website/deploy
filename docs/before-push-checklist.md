# Before Push Checklist

It's a human thing to forget and make errors. That's why you should simply use
this short checklist before pushing your final (rebased) commit in preparation for
Pull Request.




## Always

* `make lint`




## New Role

If in your PR you're adding new role, make sure to run:

* `make ami_debug ROLE=...`
* `make vagrant ROLE=...`
