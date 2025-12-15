import { Component, OnInit } from '@angular/core';
import { CartService } from "../../../service/cart.service";
import { Item } from "../../../model/item";

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {

  cartItems: Item[] = [];
  totalAmount: number = 0;
  totalSize: number = 0;

  constructor(public cartService: CartService) {}

  ngOnInit(): void {
    this.cartService.loadFromLocalStorage();

    this.cartItems = this.cartService.items;
    this.updateTotals();
  }

  updateTotals() {
    this.totalSize = this.cartItems.reduce((sum, item) => sum + item.quantity, 0);
    this.totalAmount = this.cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  }
}
