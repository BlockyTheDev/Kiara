package de.mcgregordev.kiara.rank.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.yaml.snakeyaml.Yaml;

@Data
@AllArgsConstructor
@ToString
public class Rank {
    
    private String name;
    private String prefix;
    private String suffix;
    private String permission;
    private int tabId;
    
}
