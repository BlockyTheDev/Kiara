#scoreboard
####a simple way to create and manage scoreboards

##dependencies

* **`rank`**

##config
* **`moduleScoreboard:`** **reather the module or the client can use this module**
* **`use-rank-module:`** **reather the rank module should be used or not**
* **`auto-update:`** **defines if the scoreboard should update automatically**
* **`update-interval:`** **defines how often the scoreboard should update**
* **`scoreboard-title:`** **defines the title for the custom scoreboard**
* **`scoreboard:`** **defines the content for the custom scoreboard**

##commands
* **`togglescoreboard: `** **toggles the sidebar on and off**

##events
* **`ScoreboardToggleEvent:`** **gets fired everytime a player ueses the togglescoreboard command**
* **`ScoreboardUpdateEvent:`** **gets fired everytime a players scoreboard is updated**

##how to use
```
Player player = Bukkit.getPlayer(playerName);

congratz, you did it.
```
