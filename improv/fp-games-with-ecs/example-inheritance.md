interface GameObject {
  void update(float delta);

  void render();
}

class Movable implements GameObject {
  // movable physics code in here
}

class Rigid implements GameObject {
  // rigid physics code here
}

class Rock extends Rigid {
  // rock code
}

class Human extends Moveable {
  // human code
}

class SpecialRigidHuman extends ??? {
  // Now we need to move "rigid" above "moveable". Or we need to extend "rigid" and copy the human code :( :(
}



Over time:

- Stuff moves up the heirachy as more shared abilities are needed
- This means a lot of child components have behavior they don't need/use
- Complex!
