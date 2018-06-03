package my.app.mapper;

/**
 * Developed by Aliaksei Lizunou.
 */
public interface Mapper<Command, Mapped, E extends Throwable> {
  Mapped map(Command command) throws E;
}
