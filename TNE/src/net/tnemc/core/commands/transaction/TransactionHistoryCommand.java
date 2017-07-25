package net.tnemc.core.commands.transaction;

import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import com.github.tnerevival.core.collection.paginate.Page;
import com.github.tnerevival.core.collection.paginate.Paginator;
import com.github.tnerevival.user.IDFinder;
import net.tnemc.core.TNE;
import net.tnemc.core.common.account.WorldFinder;
import net.tnemc.core.common.currency.CurrencyFormatter;
import net.tnemc.core.common.transaction.Transaction;
import net.tnemc.core.common.utils.MISCUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
public class TransactionHistoryCommand extends TNECommand {

  public TransactionHistoryCommand(TNE plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "history";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "h"
    };
  }

  @Override
  public String getNode() {
    return "tne.transaction.history";
  }

  @Override
  public boolean console() {
    return true;
  }

  @Override
  public String getHelp() {
    return "Messages.Transaction.History";
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    Map<String, String> parsed = getArguments(arguments);
    Player player = getPlayer(sender);
    String world = WorldFinder.getWorld(sender);
    String type = "all";
    int page = 1;

    if(parsed.containsKey("page")) {
      if(MISCUtils.isInteger(parsed.get("page"))) {
        page = Integer.parseInt(parsed.get("page"));
      }
    }

    if(parsed.containsKey("world")) {
      world = parsed.get("world");
    }

    if(parsed.containsKey("type")) {
      type = parsed.get("type");
    }
    UUID id = IDFinder.getID(player);
    List<UUID> history = TNE.manager().getAccount(IDFinder.getID(player)).getHistory().getHistroy(world);
    if(history.size() > 0) {
      Paginator paginator = new Paginator(new ArrayList<>(history), 5);

      if (page > paginator.getMaxPages()) page = paginator.getMaxPages();
      Page p = paginator.getPage(page);
      StringBuilder builder = new StringBuilder();

      for(Object obj : p.getElements()) {
        if(obj != null && obj instanceof UUID) {
          //TODO: Fix this to be better
          Transaction transaction = TNE.transactionManager().get((UUID)obj);
          builder.append(ChatColor.GREEN + transaction.getType().getName() + ChatColor.WHITE + " | ");
          builder.append(ChatColor.GREEN + player.getDisplayName() + ChatColor.WHITE + " | ");
          builder.append(ChatColor.GREEN + transaction.getWorld() + ChatColor.WHITE + " | ");
          builder.append(ChatColor.GREEN + transaction.getCost().getAmount().toPlainString() + ChatColor.WHITE + " | ");
          builder.append(ChatColor.GREEN + CurrencyFormatter.format(transaction.getWorld(), transaction.getType().initiatorBalance()) + ChatColor.WHITE + " | ");
          builder.append(ChatColor.GREEN + (transaction.getTime() + "") + ChatColor.WHITE);
        }
      }
      sender.sendMessage(builder.toString());
    }
    new Message("Messages.Account.NoTransactions").translate(world, player);
    return true;
  }
}