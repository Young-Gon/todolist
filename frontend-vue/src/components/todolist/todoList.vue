<template>
    <bottom-scroll @on-bottom="onBottom">
    <div id="TodoList">
        <v-container>
            <v-layout row wrap>
                <v-flex xs12 >
                    <v-text-field label="Solo" solo v-model="newTodoItem" placeholder="할일을 입력해 보세요"
                                  v-on:keyup.enter="addTodo">
                        <v-btn  slot="append-outer" offset-y style="top: -12px"
                                dark class="addContainer" @click="addTodo" >
                            <v-icon dark>fas fa-plus</v-icon>
                        </v-btn>
                    </v-text-field>
                </v-flex>
            </v-layout>
        </v-container>
        <v-list>
            <v-list-tile v-for="(todoItem, index) in todoItems" :key="todoItem.id" ripple @click="">
                <v-list-tile-action>
                    <v-icon color="#62acde">star</v-icon>
                </v-list-tile-action>

                <v-list-tile-title v-text="todoItem.content"></v-list-tile-title>

                <v-list-tile-content>{{todoItem.modifyAt}}
                </v-list-tile-content>

                <v-btn flat icon color="pink" @click="removeTodo(todoItem, index)">
                    <v-icon>far fa-trash-alt</v-icon>
                </v-btn>
            </v-list-tile>
        </v-list>
        <v-footer class="pa-3" fixed=true height="50" >
            <v-flex text-xs-center>
                <v-btn  round color="purple" dark @click="removeAll">Clear All</v-btn>
            </v-flex>
        </v-footer>
    </div>
    </bottom-scroll>
</template>

<script>
    import BottomScroll from 'vue-bottom-scroll-listener'

    export default {
    name: "TodoList",
    components: {
        BottomScroll
    },
    data(){
        return {
            page: 0,
            newTodoItem: '',
            todoItems: []
        }
    },
    created() {
        this.getTodoList()
    },
    methods: {
        onBottom () {
            console.log('On bottom reached ! Fetch next 3 pics')
            this.getTodoList()
        },
        async getTodoList() {
            try {
                const response = await this.axios.get(`/todolist?page=${this.page}`);
                console.log(response);
                if (response.status === 200) {
                    //this.todoItems.push(response.data.content);
                    response.data.content.forEach((item) => {
                        this.todoItems.push(item);
                    });
                    this.page+=1;
                }
            } catch (err) {
                console.log(err);
                this.$notify({
                    group: 'noti',
                    type: 'error',
                    duration: 6000,
                    title: err.response.state,
                    text: err.response.data.message,
                });
            }
        },
        async addTodo() {
            if(this.newTodoItem !==""){
                let value = this.newTodoItem && this.newTodoItem.trim();

                try {
                    const response = await this.axios.post('/todolist',{
                        content: value
                    });
                    console.log(response);
                    if (response.status === 200) {
                        this.todoItems.unshift(response.data);
                    }
                } catch (err) {
                    console.log(err);
                    this.$notify({
                        group: 'noti',
                        type: 'error',
                        duration: 6000,
                        title: err.response.state,
                        text: err.response.data.message,
                    });
                }
                this.newTodoItem='';
            }
        },
        async removeTodo(todoItem, index) {
            console.log(`click remove Item ${index}`);
            try {
                const response = await this.axios.delete(`/todolist/${todoItem.id}`);
                console.log(response);
                if (response.status === 200) {
                    //this.todoItems.push(response.data.content);
                    this.todoItems.splice(this.todoItems.findIndex(value => {
                        return value.id===response.data.id
                    }),1);
                }
            } catch (err) {
                console.log(err.response.data.message);
                this.$notify({
                    group: 'noti',
                    type: 'error',
                    duration: 6000,
                    title: err.response.state,
                    text: err.response.data.message,
                });
            }
        },
        async removeAll() {
            console.log("click remove All");
            try {
                const response = await this.axios.delete('/todolist/');
                console.log(response);
                if (response.status === 200) {
                    //this.todoItems.push(response.data.content);
                    this.todoItems = [];
                }
            } catch (err) {
                console.log(err.response.data.message);
                this.$notify({
                    group: 'noti',
                    type: 'error',
                    duration: 6000,
                    title: err.response.state,
                    text: err.response.data.message,
                });
            }
        }
    }
}
</script>

<style scoped>
    @import url('https://use.fontawesome.com/releases/v5.6.3/css/all.css');
    @import url('https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900|Material+Icons');

    .addContainer {
        float: right;
        background: linear-gradient(to right, #6478FB, #8763FB);
    }

    /*
    .content {
        flex: 1;
        margin-bottom: -50px;
        !* Prevent Chrome, Opera, and Safari from letting these items shrink to smaller than their content's default minimum size. *!
        padding: 20px;
    }

    body {
        text-align: center;
        background-color: #F6F6F8;
    }
    input {
        border-style: groove;
        width: 200px;
    }
    button {
        border-style: groove;
    }
    .shadow{
        box-shadow: 5px 10px 10px rgba(0,0,0,0.03);
    }

    input:focus {
        outline: none;
    }
    .inputBox {
        background: white;
        height: 50px;
        line-height: 50px;
        border-radius: 5px;
    }
    .inputBox input {
        border-style: none;
        font-size: 0.9rem;
    }
    .addContainer {
        float: right;
        background: linear-gradient(to right, #6478FB, #8763FB);
        display: block;
        width: 3rem;
        height: 30px;
        border-radius: 0 5px 5px 0;
    }
    .addBtn {
        color: white;
        vertical-align: middle;
    }
    ul {
        list-style-type: none;
        padding-left: 0;
        margin-top: 0;
        text-align: left;
    }

    li {
        display: flex;
        min-height: 50px;
        height: 50px;
        line-height: 50px;
        margin: 0.5rem 0;
        padding: 0 0.9rem;
        background: white;
        border-radius: 5px;
    }
    .checkBtn {
        line-height: 45px;
        color: #62acde;
        margin-right: 5px;
    }
    .removeBtn {
        margin-left: auto;
        color: #de4343;
    }
    .clearAllContainer {
        width: 8.5rem;
        height: 50px;
        line-height: 50px;
        background-color: white;
        border-radius: 5px;
        margin: 0 auto;
    }
    .clearAllBtn {
        color: #e20303;
        display: block;
    }*/
</style>