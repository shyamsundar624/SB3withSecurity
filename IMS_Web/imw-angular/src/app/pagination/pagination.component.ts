import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination',
  imports: [CommonModule],
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css'
})
export class PaginationComponent {
@Input() currentPage:number=1
@Input() totalPage:number=1
@Output() pageChange=new EventEmitter<number>;

//method to generate page numbers
get pageNumbers(){
  return Array.from({length: this.totalPage},(_,i)=>i+1);
}

//method to handle page change
onPageChange(page:number):void{
  if(page>1 && page<=this.totalPage){
    this.pageChange.emit(page);
  }
}
}
