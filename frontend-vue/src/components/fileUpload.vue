<template>
    <div class="container">
        <div class="large-12 medium-12 small-12 cell">
            <label>File
                <input type="file" id="file" ref="file" v-on:change="handleFileUpload"/>
            </label>
            <button v-on:click="submitFile">Submit</button>
            <img v-if="fileDownloadUri!=null" v-bind:src="fileDownloadUri.fileDownloadUri">
        </div>
        <div class="large-12 medium-12 small-12 cell">
        <label>Files
            <input type="file" id="files" ref="files" multiple v-on:change="handleFilesUpload"/>
        </label>
        <button v-on:click="submitFiles">Submit</button>
            <img v-for="file in filesDownloadUri" v-bind:src="file.fileDownloadUri">
    </div>
    </div>
</template>

<script>
export default {
    name: "fileUpload",
    data(){
        return {
            file: '',
            files: [],
            fileDownloadUri: null,
            filesDownloadUri: null,
        }
    },
    methods: {
        handleFileUpload(){
            this.file = this.$refs.file.files[0];
        },
        handleFilesUpload(){
            this.files = this.$refs.files.files;
        },
        async submitFile(){
            let formData = new FormData();
            formData.append('file', this.file);
            try {
                const response = await this.axios.post('/uploadFile',
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                );
                console.log(response);
                this.fileDownloadUri=response.data;
            }catch (err) {
                console.log(err.response);
                this.$notify({
                    group: 'noti',
                    type: 'error',
                    duration: 6000,
                    title: err.response.state,
                    text: err.response.data.message,
                });
            }
        },
        async submitFiles() {
            let formData = new FormData();
            Array.from(this.files).forEach(file => {
                formData.append('files', file);
            });
            try {
                const response = await this.axios.post('/uploadMultipleFiles',
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                );
                console.log(response);
                this.filesDownloadUri = response.data;
            } catch (err) {
                console.log(err.response);
                this.$notify({
                    group: 'noti',
                    type: 'error',
                    duration: 6000,
                    title: err.response.state,
                    text: err.response.data.message,
                });
            }
        },
    }
}
</script>

<style scoped>

</style>