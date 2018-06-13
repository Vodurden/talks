# Nix

## Overview

Nix is a package manager derived around the concept of immutability

It makes packages immutable by putting every package in a directory identified by a hash
that is computed from _all_ of the packages dependencies.

This makes it trivial to:

- Have multiple versions of the same package installed at the same time
- Rollback from _anything_ (new install, system upgrade, literally any change)
- Switch to a different set of packages on a per-project basis.

In a lot of cases this can replace docker, stack, rbenv, nvm, etc...

## Nix is a language!

Nix packages are specified using the nix language which is a purely functional language that declares how
packages are built.

Nix packages have the concept of a "derivation" which describes how to build a package based on other
packages.

One cool thing is that nix comes with a bunch of existing functions to make contributing new
packages really easy.

## Compared to existing package managers

Existing package managers tend to unpack packges into the standard unix directory structure (`/etc`,
`/bin`, `/usr`). This makes things tricky to remove after-the-fact.

Nix has an extra layer of indirection:

- Everything lives in `/nix/store`.
- Packages symlink to their dependencies in `/nix/store`
  https://nixos.org/nix/manual/#chap-introduction
- Packages can be linked into a "profile" which make bundles of packages available in a particular context

## Important things to know

Using Nix generally requires reading a lot of source code. There's a great manual but I haven't figured
out how to introspect what options are available per-package so I generally need to look at the code.

The _most important_ repo is the nixpkgs repo which contains all of the packaging code for _every single
public package_. [Nixpkgs](https://github.com/NixOS/nixpkgs)

# NixOS

What is NixOS:

- NixOS is a linux distribution that uses the Nix package management scheme for _the entire operating system_
- This let's you declaratively specify _everything_
- It also makes system upgrades and rollbacks trivial.

# Handy Links

- https://nixos.org/nix/manual/#chap-introduction
- https://nixos.org/nixpkgs/manual/#overview-of-nixpkgs
