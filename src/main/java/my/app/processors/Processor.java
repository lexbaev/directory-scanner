package my.app.processors;

import java.io.IOException;

/**
 * Root interface for all the processors.
 * <p>
 * Developed by Aliaksei Lizunou.
 *
 * @param <Id>
 * @param <Request>
 */
public interface Processor<Id, Request> {

  /**
   * Runs the process.
   * @param processId
   * @param request
   * @throws IOException
   */
  void process(Id processId, Request request) throws IOException;
}
