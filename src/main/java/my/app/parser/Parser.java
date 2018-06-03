package my.app.parser;

public interface Parser<Command, Parsed> {
  Parsed parse(Command command);
}
