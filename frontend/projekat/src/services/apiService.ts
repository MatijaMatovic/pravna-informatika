import axios from "axios";

export interface RequestParams {
    path: string,
    method?: string,
    data?: any
}

const apiUrl = process.env.REACT_APP_API_ROUTE;

export default () => ({
  // path must not start with / (e.g. it must be posts/<post_id> not /posts/<post_id>)
  executeRequest: async ({ path, method = "GET", data }: RequestParams) =>
    axios.request({
      method,
      url: `${apiUrl}${path}`,
      data
    }).then((response) => {
      console.log('Ovde 1')
      console.log(response.data)
      return response.data
    }).catch((error) => {
      console.log('ERROR API SERVICE 2')
      console.log(error.message)
    }),

  fetchXML: async ({ path }: RequestParams) =>
    fetch(`${apiUrl}${path}`, {
      headers: {
        "Content-Type": "text/xml",
      },
    }).then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw response;
    }),
});
