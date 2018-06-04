package my.app.mapper;

/**
 * Root interface for all the mappers.
 *
 * Developed by Aliaksei Lizunou.
 */
public interface Mapper<Command, Mapped, E extends Throwable> {

  /**
   * Method for mapping command's properties to Mapped object properties.
   * @param command
   * @return
   * @throws E
   */
  Mapped map(Command command) throws E;
}
