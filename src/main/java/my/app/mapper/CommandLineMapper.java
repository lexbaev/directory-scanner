package my.app.mapper;

import my.app.exceptions.ValidationException;
import org.apache.commons.cli.CommandLine;

/**
 * Specified interface mapper for scan command.
 * <p>
 * Developed by Aliaksei Lizunou.
 */
public interface CommandLineMapper extends Mapper<CommandLine, MappedScanRequest, ValidationException> {
}
