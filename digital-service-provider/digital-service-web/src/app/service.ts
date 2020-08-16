export class Service{
    constructor(
        public name:string,
        public url:string,
        public serviceState:string,
        public methodType:string,
        public createdTime:string,
        public lastUpdatedTime:Date
    ){}
}