package pl.qlnus.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SlashCommandInteractionListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("ticket")) {
            Channel channel = event.getOption("channel", OptionMapping::getAsChannel);
            if (channel == null) {
                event.reply("Nie znaleziono podanego kanalu!").setEphemeral(true).queue();
                return;
            }
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("TicketBot");
            embedBuilder.setDescription("``->`` Utwórz tutaj swoj ticket, aby otrzymać **pomoc**\\n``->`` Tworzenie **fałszywych** zgłoszeń bedzie karane!");
            embedBuilder.setColor(Color.BLACK);
            embedBuilder.setFooter("TicketBot");
            event.getGuild().getTextChannelById(channel.getId()).sendMessageEmbeds(embedBuilder.build())
                    .addActionRow(StringSelectMenu.create("ticket").setPlaceholder("Wybierz kategorię pomocy!")
                            .addOptions(
                                    SelectOption.of("Pomoc discord", "discord")
                            ).build()).queue();
            event.reply("Wyslano wiadomosc na <#" + channel.getId() + ">").setEphemeral(true).queue();
        }
    }
}
