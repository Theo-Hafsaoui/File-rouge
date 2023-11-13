package univtln.hafsaoui.rouge.daos;


public record SimpleDto(String name, String description) implements Dto {

    @Override
    public String toString() {
        return "{\"name\":\"" + this.name +
                "\",\"description\": \"" + this.description +
                "\"}";
    }

}
