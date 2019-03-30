package de.mcgregordev.kiara.rank.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rank {
    
    private String name;
    private String prefix;
    private String suffix;
    private String permission;
    
}
