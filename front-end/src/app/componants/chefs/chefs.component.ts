import { Component, OnInit } from '@angular/core';
import { Chef } from "../../../model/chef";
import { ChefService } from "../../../service/chef.service";

@Component({
  selector: 'app-chefs',
  templateUrl: './chefs.component.html',
  styleUrls: ['./chefs.component.css']
})
export class ChefsComponent implements OnInit {
  chefs: Chef[] = [];
  page: number = 1;
  pageSize: number = 10;

  constructor(private chefService: ChefService) { }

  ngOnInit(): void {
    this.getAllChefs();
  }

  getAllChefs() {
    this.chefService.getAllChefs(this.page, this.pageSize).subscribe(
      result => this.chefs = result

    );
  }
}
