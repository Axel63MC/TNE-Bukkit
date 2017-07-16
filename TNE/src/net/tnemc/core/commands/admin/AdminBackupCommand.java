package net.tnemc.core.commands.admin;

import com.github.tnerevival.commands.TNECommand;
import net.tnemc.core.TNE;
import org.bukkit.command.CommandSender;

/**
 * The New Economy Minecraft Server Plugin
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * Created by Daniel on 7/10/2017.
 */
public class AdminBackupCommand extends TNECommand {

  public AdminBackupCommand(TNE plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "backup";
  }

  @Override
  public String[] getAliases() {
    return new String[0];
  }

  @Override
  public String getNode() {
    return "tne.admin.backup";
  }

  @Override
  public boolean console() {
    return true;
  }

  @Override
  public String getHelp() {
    return "Messages.Admin.Backup";
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    if(TNE.instance().sqlManager().backup()) {
      sender.sendMessage("Successfully backed up all data.");
      return true;
    }
    sender.sendMessage("Something went wrong while trying to backup data!");
    return false;
  }
}