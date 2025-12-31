export class Item {
  id: number;
  name: string;
  description: string;
  price: number;
  imagePath: string;
  quantity: number;

  constructor(id: number, name: string, description: string, price: number, imagePath: string, quantity: number) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.imagePath = imagePath;
    this.quantity = quantity;
  }
}
