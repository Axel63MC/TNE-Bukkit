package net.tnemc.vaults;

import com.github.tnerevival.commands.CommandManager;
import net.tnemc.core.TNE;
import net.tnemc.core.common.configurations.MainConfigurations;
import net.tnemc.core.common.module.Module;
import net.tnemc.core.common.module.ModuleInfo;

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
 * All rights reserved.
 **/
@ModuleInfo(
    name = "Vaults",
    author = "creatorfromhell",
    version = "0.1.0"
)
public class VaultsModule extends Module {

  @Override
  public void load(TNE tne, String version) {
    tne.logger().info("Vaults Module loaded!");
  }

  @Override
  public void unload(TNE tne) {
    tne.logger().info("Vaults Module unloaded!");
  }

  @Override
  public void registerMainConfigurations(MainConfigurations configuration) {
    configuration.configurations.put("Core.Bank.Enabled", false);
    configuration.configurations.put("Core.Vault.Enabled", false);
    configuration.configurations.put("Core.Vault.Sign", false);
    configuration.configurations.put("Core.Vault.Command", true);
    configuration.configurations.put("Core.Vault.NPC", false);
    configuration.configurations.put("Core.Vault.Connected", false);
    configuration.configurations.put("Core.Vault.Cost", 20.0);
    configuration.configurations.put("Core.Vault.Rows", 3);
    configuration.configurations.put("Core.Vault.MultiManage", false);
    configuration.configurations.put("Core.Vault.MaxViewers", 1);
  }

  @Override
  public void registerCommands(CommandManager manager) {
    super.registerCommands(manager);
  }
}